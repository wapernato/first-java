
package untitled;

public class Main {
    public static void main(String[] args) {
        Tank.ILineStep body = new Tank.BodyStep();
        Tank.ILineStep tower = new Tank.TowerStep();
        Tank.ILineStep engine = new Tank.EngineStep();

        Tank.IAssemblyLine line = new Tank.AssemblyLine(body, engine, tower);
        Tank.Product Tank = new Tank.Product();

        line.assembleProduct(Tank);



    }
}
