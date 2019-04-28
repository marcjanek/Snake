import controller.KeysControl;
import model.Model;
import controller.Controller;
import View.*;

class Main
{
    public static void main(String[] args)
    {
        Model model = new Model();
        KeysControl keysControl = new KeysControl();
        new Controller(model, new View(model, keysControl), keysControl);

    }
}
