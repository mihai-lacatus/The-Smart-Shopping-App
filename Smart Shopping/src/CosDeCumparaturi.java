import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CosDeCumparaturi
{
    private static class Client
    {

        public String nume;
        public int varsta;
        public float sum;

    }
    private static Produs[] Produse_cumparate;
    private static float suma_cheltuita;

    //metoda de citire de la tastatura
    private static Scanner input = new Scanner(System.in);

    //functie pentru a afisa produsele cumparate
    private static void AfisareCosDeCumparaturi(Produs[] p, int n)
    {
        for (int i=0; i<n; i++)
        {
            System.out.print("\n " + (i+1) + " : " + p[i].GetNume() + " --------- " + p[i].GetPret() );
        }
    }

    //functie de determinare a pretului maxim
    private static Produs ProdusMaximum(Produs[] p, int n)
    {
        Produs max = p[0];
        for (int i=1; i<n; i++)
        {
            if (p[i].GetPret() >= max.GetPret())
                max = p[i];
        }
        return max;
    }

    //functie de cautare element
    private static int CautareElement(Produs[] p, Produs pr, int n)
    {
        int j = 0;
        for (int i=0; i<n; i++)
        {
            if (pr == p[i])
            {
                j = i;
            }
        }
        return j;
    }

    //functie de ordonare
    private static void OrdonareProduseDescrescator(Produs[] p, int n)
    {
        Produs aux = new Produs();
        for (int i=0; i<n-1; i++)
        {
            for (int j=i+1; j<n; j++)
            {
                if (p[i].GetPret() < p[j].GetPret())
                {
                    aux = p[i];
                    p[i] = p[j];
                    p[j] = aux;
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException
    {
        Magazin supermarket = new Magazin();
        Client eu = new Client();

        //initializarea clientului
        try
        {
            File fis = new File("client.txt");
            Scanner in = new Scanner(fis);

            eu.nume = in.nextLine();
            eu.varsta = in.nextInt();
            eu.sum = in.nextFloat();

            in.close();

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Fisier client negasit! ");
        }

        //setarea Magazinului
        supermarket.SetProduse();
        supermarket.SetNume("LIDL");

        //inceputul cumparaturilor
        Produs maximum;

        //numarul de produse cumparate
        int j = 0;
        int n;

        //numarul de produse
        n = supermarket.GetN();
        Produse_cumparate = new Produs[n];

        maximum = ProdusMaximum(supermarket.GetProduse(), n);

        //eliminarea produselor mai mari decat suma pe care o avem
        while (maximum.GetPret() > eu.sum)
        {

            supermarket.EliminareElement(n, CautareElement(supermarket.GetProduse(), maximum, n));
            n--;
            maximum = ProdusMaximum(supermarket.GetProduse(), n);

        }

        //preluarea succesiva a produsului cu valoare maxima si apoi eliminarea sa, astfel incat excludem riscul de a avea un rest mare si favorizam numarul minimum de produse cumparate
        while ((eu.sum-maximum.GetPret()) > 0 && j<n)
        {

            eu.sum -= maximum.GetPret();

            Produse_cumparate[j] = new Produs();
            Produse_cumparate[j] = maximum;
            suma_cheltuita += maximum.GetPret();

            supermarket.EliminareElement(n, CautareElement(supermarket.GetProduse(), maximum, n));
            n--;

            maximum = ProdusMaximum(supermarket.GetProduse(), n);

            j++;
        }
        n--;

        //extragerea produselor ramase sub suma
        Produs[] produse_ramase_sub_suma = new Produs[n];
        Produs extras;

        //numarul de elemente extrase
        int k =0;

        for (int i=0; i<n; i++)
        {
            produse_ramase_sub_suma[k] = new Produs();
            extras = supermarket.GetProdus(i);
            if (extras.GetPret() > 0 && extras.GetPret() <= eu.sum)
            {
                produse_ramase_sub_suma[k] = extras;
                k++;
            }
        }

        //ordonarea produselor
        OrdonareProduseDescrescator(produse_ramase_sub_suma, k);

        //continuarea cumparaturilor
        for (int i=0; i<k; i++)
        {
            if (eu.sum - produse_ramase_sub_suma[i].GetPret() > 0)
            {
                eu.sum -= produse_ramase_sub_suma[i].GetPret();
                Produse_cumparate[j] = new Produs();
                Produse_cumparate[j] = produse_ramase_sub_suma[i];
                suma_cheltuita += produse_ramase_sub_suma[i].GetPret();
                j++;
            }
        }

        //emiterea bonului si afisarea restului
        System.out.print("\n Bun venit la " + supermarket.GetNume() + " , " + eu.nume + " !");

        System.out.print("\n\n Bonul dumneavoastra este: ");
        AfisareCosDeCumparaturi(Produse_cumparate, j);

        System.out.printf("\n\n Ati cheltuit: %.2f lei ", suma_cheltuita);
        System.out.printf("\n Restul: %.2f lei ", eu.sum);

        System.out.print("\n\n Va multumim si va mai asteptam!");
    }
}