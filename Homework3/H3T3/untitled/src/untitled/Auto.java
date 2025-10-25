package untitled;

public class Auto {


    public interface IProductPart {

    }

    public static class Part implements IProductPart {
        private final String name;
        public Part(String name) { this.name = name; }
        @Override public String toString() { return name; }
    }

    public interface IProduct {
        void installFirstPart(IProductPart part);
        void installSecondPart(IProductPart part);
        void installThirdPart(IProductPart part);
    }

    public static class Product implements IProduct {

        private IProductPart firstPart;
        private IProductPart secondPart;
        private IProductPart thirdPart;

        @Override
        public void installFirstPart(IProductPart part) {
            this.firstPart = part; // сохраняем ссылку на переданную деталь
            System.out.println("Первая часть установлена");
        }

        @Override
        public void installSecondPart(IProductPart part) {
            this.secondPart = part;
            System.out.println("Вторая часть установлена");
        }

        @Override
        public void installThirdPart(IProductPart part) {
            this.thirdPart = part;
            System.out.println("Третья часть установлена");
        }

    }



    public interface IAssemblyLine {
        IProduct assembleProduct(IProduct product);
    }
    public static class AssemblyLine implements IAssemblyLine {
        private final ILineStep first;
        private final ILineStep second;
        private final ILineStep third;

        public AssemblyLine(ILineStep first, ILineStep second, ILineStep third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public IProduct assembleProduct(IProduct product) {
            product.installFirstPart(first.buildProductPart());
            product.installSecondPart(second.buildProductPart());
            product.installThirdPart(third.buildProductPart());
            return product;
        }
    }




    public interface ILineStep {
        IProductPart buildProductPart();

    }

    public static class BodyworkStep implements ILineStep {
        @Override
        public IProductPart buildProductPart(){
            System.out.println("Кузов собран");
            return new Part("Кузов");
        }
    }


    public static class ChassisStep implements ILineStep {
        @Override
        public IProductPart buildProductPart() {
            System.out.println("Шасси собрано");
            return new Part("Шасси");

        }
    }

    public static class EngineStep implements ILineStep {
        @Override
        public IProductPart buildProductPart() {
            System.out.println("Двигатель собран");
            return new Part("Двигатель");
        }
    }

}

