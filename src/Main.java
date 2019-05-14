import controller.Controller;
import model.Model;
import view.View;

/**
 * class connecting classes in MVC model
 */
final class Main
{
    public static void main(String[] args)
    {
        final Model model = new Model();
        final Controller controller = new Controller(model);
        final View view = new View(model, controller);
        controller.start(view);
    }
}