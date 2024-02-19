package n7rider.ch7.object_oriented_design;

/**
 * Call Center: Imagine you have a call center with three levels of employees: respondent, manager,
 * and director. An incoming telephone call must be first allocated to a respondent who is free. If the
 * respondent can't handle the call, he or she must escalate the call to a manager. If the manager is not
 * free or not able to handle it, then the call should be escalated to a director. Design the classes and
 * data structures for this problem. Implement a method dispatchCall() which assigns a call to
 * the first available employee.
 *
 * After completing:
 * ---
 *
 * After comparing with solution:
 * The solution tried to solve dispatchCall() in detail.
 * It says that you should usually design for long-term code flexibility and maintenance. That's tricky, to be
 * more realistic, you might be tempted to talk about shifts and adding, removing people off a shift and then maintaining a
 * queue. I guess it's best to ask before adding entities not mentioned in the problem.
 */
public class Question7_2 {
    class Call {
        String number;
        Employee employee; // assigned person
    }

    enum EmployeeType {
        Respondent,
        Manager,
        Director
    }

    enum Status {
        Available,
        Busy
    }

    class Employee {
        EmployeeType employeeType;
        String name;
        Status status;
    }

    abstract class CallCenter {
        Employee[] employees;

        abstract Call dispatchCall(Call call);
        abstract void endCall(Call call);
    }

}


/**
 * Entities:
 * Call Center
 * Employee
 * EmployeeType
 * Call
 *
 * Adding methods, fields
 * Call Center
 *  Employee[]
 *  EmployeeType
 *  PriorityQueue EmployeePool // to put employees first, manager next, director last
 *
 *  dispatchCall(Call)
 *  endCall(Person)
 *
 * Employee
 *  EmployeeType
 *  Name
 *
 * Call
 *  String num
 *
 *
 * dispatchCall(Call)
 *  Pop the first entry in the priority queue
 *
 *  // If we can't use a queue
 *  Iterate through a list of employees
 *    Stop if employee is found
 *    Collect if director or manager is found
 *
 *    if employee and manager are not found
 *      return director
 *    else
 *      return manager
 *
 *    // We can have a management list to simplify the logic too
 *
 *
 *
 *
 *
 */