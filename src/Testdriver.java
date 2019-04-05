import java.awt.EventQueue;

public class Testdriver
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    ServerGui frame = new ServerGui();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    AnmeldeGui anmeldeGui = new AnmeldeGui();
                    anmeldeGui.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    AnmeldeGui anmeldeGui = new AnmeldeGui();
                    anmeldeGui.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
