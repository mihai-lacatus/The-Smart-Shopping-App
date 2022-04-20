import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Magazin
{
    private String Nume;
    private int n; // numarul de produse
    private Produs[] produs;

    //constructor
    public Magazin()
    {
        Nume = " ";
        produs  = new Produs[0];
        n = 0;
    }

    //setteri
    public void SetNume(String Nume)
    {
        this.Nume = Nume;
    }
    public void SetProduse() throws FileNotFoundException
    {
        try
        {
            //initializare accesor fisier
            File f = new File("produse.txt");
            //initializare cititor din fisier
            Scanner input = new Scanner(f);

            //initializare n
            String nr = input.nextLine();
            n = Integer.parseInt(nr);

            produs = new Produs[n];

            for (int i = 0; i < n; i++)
            {
                produs[i] = new Produs();
                produs[i].SetNume(input.nextLine());

                String p = input.nextLine();
                produs[i].SetPret(Float.parseFloat(p));
            }

            input.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Fisier magazin negasit! ");
        }


    }
    //getteri
    public String GetNume()
    {
        return Nume;
    }
    public int GetN()
    {
        return n;
    }
    public Produs GetProdus(int i)
    {
        return produs[i];
    }
    public Produs[] GetProduse()
    {
        return produs;
    }

    //metoda de eliminare element de tip produs
    public void EliminareElement(int n, int i)
    {
        for (int j = i; j < n-1; j++)
            produs[j] = produs[j+1];
        produs[n-1].SetNume(" ");
        produs[n-1].SetPret(0);
    }


}