package houserent.view;


import houserent.domain.House;
import houserent.service.HouseService;
import houserent.utils.Utility;


public class HouseView {
    
    Boolean loop = true;
    char key = ' ';//接收用户输入
    private HouseService houseService = new HouseService(10);

    public void MainMenu(){
        System.out.println("======================房屋出租系统====================");
        System.out.println("\t\t 1 新 增 房 源");
        System.out.println("\t\t 2 查 找 房 源");
        System.out.println("\t\t 3 删 除 房 屋");
        System.out.println("\t\t 4 修 改 房 屋 信 息");
        System.out.println("\t\t 5 房 屋 列 表");
        System.out.println("\t\t 6 退    出");
        do{
            System.out.print("请选择(1~6)：");
            key = Utility.readMenuSelection();
            switch (key){
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    updateHouse();
                    break;
                case '5':
                    listHouse();
                    break;
                case '6':
                    exitHouse();
                    break;
            }
        }while(loop);
        System.out.println("==========退出房屋系统============");
    }

    public void listHouse(){
        System.out.println("===================房屋列表======================");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态（出租/未出租）");
        House[] houses  = houseService.list();
        for (int i = 0; i < houses.length; i++) {
            if(houses[i] == null) break;
            System.out.println(houses[i]);
        }
        System.out.println("==================房屋列表显示完毕===================");
    }

    public void addHouse(){
        System.out.println("===================添加房屋信息=====================");
        System.out.print("姓名：");
        String name = Utility.readString(8);
        System.out.print("电话：");
        String phone = Utility.readString(12);
        System.out.print("地址：");
        String address = Utility.readString(16);
        System.out.print("月租：");
        int rent = Utility.readInt();
        System.out.print("状态：");
        String state = Utility.readString(3);

        House newHouse = new House(0, name, phone, address, rent, state);
        if(houseService.add(newHouse)){
            System.out.println("添加成功");
        }else {
            System.out.println("添加失败");
        }
    }

    //删除
    public void delHouse(){
        System.out.println("==================删除房屋====================");
        System.out.print("请选择编号(-1退出)");
        int number = Utility.readInt();
        if(number  == -1){
            System.out.println("================放弃删除=================");
            return;
        }

        System.out.println("确认是否删除?" );
        char select = Utility.readConfirmSelection();
        if(select == 'Y'){
            if(houseService.del(number)){
                System.out.println("==============删除成功===============");
            }else{
                System.out.println("==============删除失败===============");
            }
        }else {
            System.out.println("=========放弃删除========");
        }
    }

    public void exitHouse(){
        char select = Utility.readConfirmSelection();
        if(select == 'Y') {
            System.out.println("您退出了程序~");
            loop = false;
        }
    }

    public void findHouse(){
        System.out.println("请输入你要查找的id");
       int number = Utility.readInt();
       if(houseService.findHouse(number))
           System.out.println("=======================================================");
       else {
           System.out.println("=========查找失败=======");
        }
    }

    public void updateHouse(){
        System.out.println("=====================修改房屋信息======================");
        System.out.println("请选择待修改的房屋编号（-1退出）：");
        int number = Utility.readInt();
        if( number == -1){
            System.out.println("================退出修改================");
            return;
        }

        //接收返回对象，传址引用
        House house = houseService.findById(number);
        if (house == null) {
            System.out.println("=========修改房屋信息编号不存在=========");
            return;
        }

        System.out.print("姓名(" + house.getName() + "): ");
        //这里如果用户直接回车表示不修改信息 默认""
        String name = Utility.readString(8, "");
        if (!"".equals(name)) {//修改
            house.setName(name);
        }
        System.out.print("电话(" + house.getPhone() + "):");
        String phone = Utility.readString(12, "");
        if (!"".equals(phone)) {
            house.setPhone(phone);
        }
        System.out.print("地址(" + house.getAddress() + "):");
        String address = Utility.readString(18, "");
        if (!"".equals(address)) {
            house.setAddress(address);
        }
        System.out.print("租金(" + house.getRent() + "):");
        int rent = Utility.readInt(-1);
        if (rent != -1) {
            house.setRent(rent);
        }
        System.out.println("状态(" + house.getState() + "):");
        String state = Utility.readString(3, "");
        if (!"".equals(state)) {
            house.setState(state);
        }
        System.out.println("===========修改房屋信息成功=========");

    }
}









