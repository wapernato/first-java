package H3T2;


public class Warehouse {
    public static void main(String[] args) {
        double Iphone14 = 3;
        double Iphone14pro = 3.5;
        double Iphone15 = 3.5;
        double Iphone15pro = 4.2;

        StorageArea.ReceptionArea reception = new StorageArea.ReceptionArea(120);
        StorageArea.ShippingArea shipping = new StorageArea.ShippingArea(80);

        StorageArea.AllArea all = new StorageArea.AllArea(  // уточнить
                reception.getLoad(),
                shipping.getLoad()
        );

        int answer = all.AllLoad();  // уточнить
        System.out.println(answer);
    }
}


