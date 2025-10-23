package H3T2;


public class Warehouse {
    public static void main(String[] args) {

        StorageArea.ReceptionArea reception = new StorageArea.ReceptionArea(38);
        StorageArea.ShippingArea shipping = new StorageArea.ShippingArea(45);

        StorageArea.AllArea all = new StorageArea.AllArea(  // уточнить
                reception.getLoad(),
                shipping.getLoad()
        );

        int answer = all.AllLoad();  // уточнить
        System.out.println(answer);
    }
}


