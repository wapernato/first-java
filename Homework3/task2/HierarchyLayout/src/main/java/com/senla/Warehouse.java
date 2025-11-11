package H3T2;

import H3T2.area.AllArea;
import H3T2.area.ReceptionArea;
import H3T2.area.ShippingArea;

public class Warehouse {
    public static void main(String[] args) {
        ReceptionArea reception = new ReceptionArea(2);
        ShippingArea shipping = new ShippingArea(3);

        AllArea all = new AllArea(
                reception.getLoad(),
                shipping.getLoad()
        );

        int answer = all.AllLoad();
        System.out.println(answer);
    }
}
