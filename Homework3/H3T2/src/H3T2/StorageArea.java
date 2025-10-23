package H3T2;

import java.util.Objects;

class StorageArea {

        public static class ReceptionArea {
            int load;
            public ReceptionArea(int load) { this.load = load; }  // уточнить
            public int getLoad() { return load; }
        }

        public static class ShippingArea {
            int load;
            public ShippingArea(int load) { this.load = load; }  // уточнить
            public int getLoad() { return load; }
        }


        public static class AllArea {
            private int receptionLoad;  // уточнить
            private int shippingLoad;  // уточнить

            public AllArea(int receptionLoad, int shippingLoad) {
                this.receptionLoad = receptionLoad;
                this.shippingLoad = shippingLoad;
            }

            public int AllLoad() {
                if (receptionLoad + shippingLoad < 200) {
                    return receptionLoad + shippingLoad;
                } else {
                    System.out.println("Error");
                    return -1;

                }
            }
        }
    }