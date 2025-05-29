import model.Voo;
import model.Passagem;
import services.ServicoReservas;
import services.ServicoTransferencias;

public class SistemaAereoApp {
    public static void main(String[] args) {
        // Criação de voo
        Voo voo101 = new Voo("V101", "São Paulo (GRU)", "Nova York (JFK)");
        
        // Serviços
        ServicoReservas reservas = new ServicoReservas();
        ServicoTransferencias transferencias = new ServicoTransferencias();
        
        // Reservas
        Passagem primeiraClasse = reservas.reservarPassagemPrimeiraClasse(
            voo101, "PC-001", "João Silva", 8500.00
        );
        
        Passagem economica = reservas.reservarPassagemEconomica(
            voo101, "EC-205", "Maria Mendes", 2200.00, 15
        );
        
        // Transferência
        transferencias.transferirPassagem(primeiraClasse, "Carlos Brito");
        
        // Listagem
        System.out.println("\nPassagens no voo " + voo101.getNumeroVoo() + ":");
        for (Passagem p : voo101.getPassagens()) {
            System.out.println("-> " + p.getCodigo() + 
                " (" + p.getClass().getSimpleName() + ")" +
                " - Proprietário: " + p.getProprietario());
        }
    }
}