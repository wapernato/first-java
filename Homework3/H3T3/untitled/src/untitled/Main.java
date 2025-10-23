
package untitled;

public class Main {
    public static void main(String[] args) {
        Laptop.ILineStep body = new Laptop.BodyStep();
        Laptop.ILineStep board = new Laptop.MotherboardStep();
        Laptop.ILineStep screen = new Laptop.ScreenStep();

        Laptop.IAssemblyLine line = new Laptop.AssemblyLine(body, board, screen);
        Laptop.Product laptop = new Laptop.Product();

        line.assembleProduct(laptop);


    }
}
