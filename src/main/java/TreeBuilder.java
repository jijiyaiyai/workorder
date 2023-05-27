import com.fr.workorder.entity.Company;
import com.fr.workorder.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

class node{
    public node(String name,String father){
        this.name = name;
        this.father = father;
        children = new LinkedHashMap<>();
    }
    Map<String,node> children;
    String name;
    String father;
}

public class TreeBuilder {
    public static void build_GD() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("GD");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/resources/GDtree"));
        Scanner scanner = new Scanner(classPathResource.getInputStream());
        String prefix = "GD";
        int citycnt = 0;

        String[] province = scanner.nextLine().split(" ");
        String p = province[0];//先建立省-市结构
        node root = new node(p, null);
        for (int i = 1; i < province.length; i++) {
            root.children.put(province[i], new node(province[i], p));
        }
        String provinceCompanyID = prefix+String.format("%02d",citycnt)+String.format("%02d",0)+String.format("%02d",0);
        String provinceCompanyName = p+"分公司";
        StringBuilder provinceChildrenID = new StringBuilder();

        // 建立市-区/县结构
        String line;
        for (Map.Entry<String,node>cityEntry: root.children.entrySet()){
            if(cityEntry.getKey().equals("东莞市") || cityEntry.getKey().equals("中山市")){
                // 如果是这两个市的话，直接建立镇公司和市公司
                int towncnt = 0;
                line = scanner.nextLine();
                String[] city = line.split(" ");
                String c = city[0];
                String cityCompanyID = prefix+String.format("%02d",++citycnt)+String.format("%02d",0)+String.format("%02d",0);
                String cityCompanyName = p+c+"分公司";
                StringBuilder cityChildrenID = new StringBuilder();
                provinceChildrenID.append(cityCompanyID).append(";");
                for (int i = 1; i < city.length; i++) {
                    // 乡镇级公司没有子公司了，所以直接建立公司
                    String townCompanyID = prefix+String.format("%02d",citycnt)+String.format("%02d",0)+String.format("%02d",++towncnt);
                    String townCompanyName = p+c+city[i]+"分公司";
                    Company tc = new Company(townCompanyID,townCompanyName,4,p,c,null,city[i],cityCompanyID,null);
                    objectOutputStream.writeObject(tc);

                    cityChildrenID.append(townCompanyID).append(";");

                    cityEntry.getValue().children.put(city[i], new node(city[i], c));
                }
                // 这个市的结构建立完毕，建立市公司
                Company cc = new Company(cityCompanyID,cityCompanyName,2,p,c,null,null,provinceCompanyID, cityChildrenID.isEmpty()?null:cityChildrenID.toString());
                objectOutputStream.writeObject(cc);

                continue;
            }

            int districtcnt = 0;
            // 遍历市，初始化市-区的结构
            line = scanner.nextLine();
            String[] city = line.split(" ");
            String c = city[0];
            for (int i = 1; i < city.length; i++) {
                cityEntry.getValue().children.put(city[i], new node(city[i], c));
            }
            String cityCompanyID = prefix+String.format("%02d",++citycnt)+String.format("%02d",districtcnt)+String.format("%02d",0);
            String cityCompanyName = p+c+"分公司";
            StringBuilder cityChildrenID = new StringBuilder();
            provinceChildrenID.append(cityCompanyID).append(";");

            // 遍历该市内的所有区，建立区-乡镇结构
            for(Map.Entry<String,node>districtEntry: cityEntry.getValue().children.entrySet()){
                int towncnt = 0;
                // 按顺序读取区-乡镇的结构
                line = scanner.nextLine();

                String[] district = line.split(" ");
                String d = district[0];
                String districtCompanyID = prefix+String.format("%02d",citycnt)+String.format("%02d",++districtcnt)+String.format("%02d",towncnt);
                String districtCompanyName = p+c+d+"分公司";
                StringBuilder districtChildrenID = new StringBuilder();
                cityChildrenID.append(districtCompanyID).append(";");

                for (int i = 1; i < district.length; i++) {
                    // 乡镇级公司没有子公司了，所以直接建立公司
                    String townCompanyID = prefix+String.format("%02d",citycnt)+String.format("%02d",districtcnt)+String.format("%02d",++towncnt);
                    String townCompanyName = p+c+d+district[i]+"分公司";
                    Company tc = new Company(townCompanyID,townCompanyName,4,p,c,d,district[i],districtCompanyID,null);
                    objectOutputStream.writeObject(tc);

                    districtChildrenID.append(townCompanyID).append(";");

                    districtEntry.getValue().children.put(district[i], new node(district[i], d));
                }

                // 这个区/县的结构建立完毕，建立区/县公司
                Company dc = new Company(districtCompanyID, districtCompanyName, 3,p,c,d,null,cityCompanyID,districtChildrenID.isEmpty()?null:districtChildrenID.toString());
                objectOutputStream.writeObject(dc);
            }

            // 这个市的结构建立完毕，建立市公司
            Company cc = new Company(cityCompanyID,cityCompanyName,2,p,c,null,null,provinceCompanyID, cityChildrenID.isEmpty()?null:cityChildrenID.toString());
            objectOutputStream.writeObject(cc);
        }
        // 这个省的结构建立完毕，建立省公司
        Company pc = new Company(provinceCompanyID,provinceCompanyName,1,p,null,null,null,null,provinceChildrenID.isEmpty()?null:provinceChildrenID.toString());
        objectOutputStream.writeObject(pc);
        // 写进一个null防止报错
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static void read(String name) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ClassPathResource(name).getInputStream());
        Company c = (Company) objectInputStream.readObject();
        while(c!=null){
            System.out.println(c);
            c = (Company) objectInputStream.readObject();
        }
        objectInputStream.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        build_GD();
        read("GDtree");
    }
}
