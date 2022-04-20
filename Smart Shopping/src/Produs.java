public class Produs
{
    private String Nume;
    private float Pret;

    //constructor
    public Produs()
    {
        Nume = " ";
        Pret = 0;
    }

    //setteri
    public void SetNume(String Nume)
    {
        this.Nume = Nume;
    }
    public void SetPret(float Pret)
    {
        this.Pret = Pret;
    }

    //getteri
    public String GetNume()
    {
        return Nume;
    }
    public float GetPret()
    {
        return Pret;
    }


}