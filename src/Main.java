import model.Model;
import controller.Controller;
import View.*;

class Main
{
    public static void main(String[] args)
    {
        Model model = new Model();
        new Controller(model, new View(model));

    }
}
