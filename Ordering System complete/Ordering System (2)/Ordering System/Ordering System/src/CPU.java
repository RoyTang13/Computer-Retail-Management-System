
public class CPU extends Product {
    private int cores;
    private int threads;

    public CPU(String productId, String name, double price, int quantity, int cores, int threads) {
        super(productId, name, price, quantity);
        this.cores = cores;
        this.threads = threads;
    }

    @Override
    public String getCategory() {
        return "CPU";
    }

    @Override
    public String toString() {
          return String.format("%-10s | %-35s | %-10.2f | %-7d | %-5d | %-7d",
                         productId, name, price, quantity, cores, threads);
    }
    
    public int getCores() { return cores; }
    public int getThreads() { return threads; }

    @Override
    public String toDataString() {
          return String.format("%s,%s,%s,%.2f,%d,%d,%d",
                         getCategory(), productId, name, price, quantity, cores, threads);
    }
}
