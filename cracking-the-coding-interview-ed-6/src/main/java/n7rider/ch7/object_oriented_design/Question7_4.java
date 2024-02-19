package n7rider.ch7.object_oriented_design;

import java.util.Map;

/**
 * Parking Lot: Design a parking lot using object-oriented principles.
 *
 * After finishing:
 * Floors is not used anywhere
 * My system is simpler because it doesn't assign a spot for a car. But that is closer to real life.
 * A system that directs user to go to a spot will have more accurate lot count (Otherwise, Small car can
 * park in large car's spot, and mess up calculations)
 *
 *
 * After comparing with solution:
 * The solution considers contiguous spots for bus too. We can do that if we are directing cars to go to an
 * assigned spot. In that case:
 * lotsOpen Map will store <CarType, Lot>
 * Lot will have Floor, index
 * The startSession in EntryGate will assign a lot from the beginning or will close smallest
 * gaps first (if some vehicle left earlier). Large gaps can take in a bus.
 * If small car space is out, it can accommodate one in a large car space.
 *
 *
 *
 */
public class Question7_4 {
    class ParkingLot {
        // Singleton

        Floor[] floors;
        Map<CarType, Integer> lotsOpen;
        Map<String, ParkSession> parkSessions; // key = vehicle reg Id
        Gate[] gates;
    }

    class Floor {
        int level;
    }

    enum CarType {
        SMALL, LARGE, EV, RESERVED, DISABLED
    }

    class ParkSession {
        String make;
        CarType carType;
    }

    abstract class Gate {
        abstract void openGate();
        abstract void closeGate();
    }

    abstract class EntryGate extends Gate {
        abstract ParkSession startSession();
        abstract String printAndIssueToken(String regId);
        abstract void reduceLotCount(CarType carType);
    }

    abstract class ExitGate extends Gate {
        abstract ParkSession getSession(String regId);
        abstract void payAndEndSession(ParkSession parkSession);
        abstract void increaseLotCount(CarType carType);
    }


}

/**
 * Components:
 * Parking Lot // Singleton
 * Car
 * Lot
 * Lot Type
 * Floor
 * Gate
 * GateType
 * PaymentSystem
 * ParkSession
 * ParkSessionManager
 *
 * Add fields and methods:
 * Parking Lot // Singleton
 *   Floor[]
 *      int level;
 *      Map<LotType, List<Lot>> lotsOpen;
 *
 *          Lot - Lot Type, Floor, ParkSession
 *              // Assume each car will be parked at the right lot type
 *              Lot Type - Small | Large | EV | Reserved | Disabled
 *              ParkSession
 *                 Make
 *                 Color
 *                 Reg.Id
 *                 startTime
 *                 endTime
 *
 *   Gate[] gates;
 *      Gate - Gate Id, Gate Type, ParkSessionManager
 *          Entry Gate - same as Gate
 *          Exit Gate - has PaymentSystem in addition
 *          GateType - Entry | Exit
 *
 * Flow:
 * Car arrives at gate
 *
 * Gate.startParkSession
 * Gate.generateToken
 * Gate.openGate
 *
 * ParkingLot.subtractOpenLots(Parksession, lotsOpen)
 *
 *
 * Car leaves
 * Gate.endParksession
 * Gate.pay
 * Gate.addOpenLots(lotsOpen)
 *
 *
 *
 */