//1.Normal
class Data implements Serializable {
    private int i;
    private int tabela[];

    public Data(int x) {
        i = x;
        tabela = new int[10];
        for (int j = 0; j < 10; j++)
            tabela[j] = x;
    }

    public String toString() {
        return Integer.toString(i);
    }

    public void print_conteudo() {
        for (int j = 0; j < 10; j++)
            System.out.print(tabela[j] + " ");
        System.out.println();
    }
}

// 2.Overwriting writeObject/readObject
public class DemoClass implements Serializable {
    private int _dat = 3;
    private static int _sdat = 2;

    private void writeObject(ObjectOutputStream o) throws IOException {
        o.writeInt(_dat);
        o.writeInt(_sdat);
    }

    private void readObject(ObjectInputStream i) throws IOException, ClassNotFoundException {
        _dat = i.readInt();
        _sdat = i.readInt();
    }
}