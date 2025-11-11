package H3T2.area;

public class AllArea {
    private final int receptionLoad;
    private final int shippingLoad;

    public AllArea(int receptionLoad, int shippingLoad) {
        this.receptionLoad = receptionLoad;
        this.shippingLoad = shippingLoad;
    }

    // Сохраняю имя метода как в исходнике
    public int AllLoad() {
        if (receptionLoad + shippingLoad < 200) {
            return receptionLoad + shippingLoad;
        } else {
            System.out.println("Error");
            return -1;
        }
    }

    // (опционально, более каноничное имя)
    public int getTotalLoad() {
        return AllLoad();
    }
}
