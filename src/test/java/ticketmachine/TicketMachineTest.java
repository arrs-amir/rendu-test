package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMachineTest {
    private static final int PRICE = 50; // Une constante
    private TicketMachine machine; // L'objet à tester

    @BeforeEach
    public void setUp() {
        machine = new TicketMachine(PRICE); // Initialisation de l'objet avant chaque test
    }

    @Test
    // S1 : Vérifie que le prix affiché correspond à l'initialisation
    void priceIsCorrectlyInitialized() {
        assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
    }

    @Test
    // S2 : Vérifie que la balance change quand on insère de l'argent
    void insertMoneyChangesBalance() {
        machine.insertMoney(10);
        machine.insertMoney(20);
        assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
    }

    @Test
    // S3 : Vérifie qu'on n'imprime pas le ticket si le montant inséré est insuffisant
    void printTicketFailsIfNotEnoughMoney() {
        machine.insertMoney(10); // Insérer moins que le prix
        assertFalse(machine.printTicket(), "le montant insere n'est pas suffisant");
    }

    @Test
    // S4 : Vérifie qu'on imprime le ticket si le montant inséré est suffisant
    void printTicketSucceedsIfEnoughMoney() {
        machine.insertMoney(50); // Insérer exactement le prix
        assertTrue(machine.printTicket(), "Le ticket peut  être imprimé");
    }

    @Test
    // S5 : Vérifie que la balance est décrémentée du prix du ticket après l'impression
    void balanceIsDecreasedWhenTicketIsPrinted() {
        machine.insertMoney(100); // Insérer plus que le prix
        machine.printTicket();
        assertEquals(100 - PRICE, machine.getBalance(), "La balance n'a pas été correctement décrémentée");
    }

    @Test
    // S6 : Vérifie que le montant collecté est mis à jour quand on imprime un ticket
    void totalIsUpdatedWhenTicketIsPrinted() {
        machine.insertMoney(50); // Insérer exactement le prix
        machine.printTicket();
        assertEquals(PRICE, machine.getTotal(), "Le total n'a pas été correctement mis à jour");
    }

    @Test
    // S7 : Vérifie que refund() rend correctement la monnaie
    void refundReturnsCorrectAmount() {
        machine.insertMoney(100); // Insérer un montant supérieur
        machine.printTicket();
        assertEquals(100 - PRICE, machine.refund(), "La monnaie rendue est incorrecte");
    }

    @Test
    // S8 : Vérifie que refund() remet la balance à zéro
    void refundResetsBalance() {
        machine.insertMoney(100);
        machine.refund();
        assertEquals(0, machine.getBalance(), "La balance n'a pas été réinitialisée après remboursement");
    }

    @Test
    // S9 : Vérifie qu'on ne peut pas insérer un montant négatif
    void cannotInsertNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-1), "L'insertion d'un montant négatif n'a pas levé d'exception");
    }

    @Test
    // S10 : Vérifie qu'on ne peut pas créer une machine avec un prix négatif
    void cannotCreateMachineWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-1), "La création d'une machine avec un prix négatif n'a pas levé d'exception");
    }
}