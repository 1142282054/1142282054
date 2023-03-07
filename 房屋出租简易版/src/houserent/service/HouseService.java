package houserent.service;

import houserent.domain.House;

public class HouseService {


    private House[] houses;
    private int houseNumber = 1;
    private int idCounter  = 1;

    public HouseService(int size) {
        houses  = new House[size];
        // 初始化房屋信息
        houses[0] = new House(1,"min","141","江门",2000,"未出租");
    }
    /**
     *  返回房屋信息
     * @return 房屋信息列表
     */
    public House[] list (){
        return houses;
    }

    /**
     * 添加房屋信息
     * @param newHouse 房屋信息
     * @return 添加成功true
     */
    public boolean add(House newHouse){
        //判断是否满了      houses.length = 10
        if(houseNumber == houses.length){
            System.out.println("已满，不能添加");
            return false;
        }
        houses[houseNumber++] = newHouse;
        newHouse.setId(++idCounter);
        return true;
    }

    /**
     * 删除房屋信息
     * @param number 房屋索引号
     * @return 删除成功为true
     */
    public boolean del(int number){
        //找出是否有这个编号
        int index = -1;
        for (int i = 0; i < houseNumber; i++) {
            if( houses[i].getId() == number) {
                index = i;
                break;
            }
        }
        if(index == -1){
            System.out.println("target is not found");
            return false;
        }
        //开始删除
        for (int i = index; i < houseNumber-1; i++) {
            houses[i] = houses[i+1];
        }
        houses[--houseNumber] = null;
        return true;
    }


    /**
     * 查找房屋功能
     * @param number 房屋索引号
     * @return 查找成功为true
     */
    public boolean findHouse(int number){
        //找出是否有这个编号
        int index = -1;
        for (int i = 0; i < houseNumber; i++) {
            if( houses[i].getId() == number) {
                index = i;
                break;
            }
        }
        if(index == -1){
            System.out.println("target is not found");
            return false;
        }
        System.out.println(houses[index]);
        return true;
    }
    //findbyId方法 返回House对象或null
    public House findById(int findId){

        for (int i = 0; i < houseNumber; i++) {
            if (findId == houses[i].getId()){
                return houses[i];
            }
        }
        return null;
    }
}
