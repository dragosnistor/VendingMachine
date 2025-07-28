Exercise - Vending Machine

Code a Vending Machine which:

    Accepts coins of 0.05, 0.10, 0.20, 0.50, 1, 2 coins (the currency doesn’t matter)
    Allow user to select products Coke(1.50), Pepsi(1.45), Water(0.90)
    Allow user to take refund by cancelling the request.
    Return selected product and remaining change if any
    Allow reset operation for vending machine supplier.

Constraints:

    Databases are not allowed. Save the state in memory.
    Assume the UI is console based; no need to write a web or desktop GUI unless that is your preference
    You can use Java 1.8, 11 or 17 and an IDE of your choice
    Use the minimum number of external libraries 


To Do List:
- Interface for UI, implementation for Console and potentially others
- Interface for Repository, implementation for Memory and potentially others
- Interface for Request / Response, Implementation for Response where Response Status as List<String>
- Response like HttpResponse / ResponseEntity with multiple objects as Response Status and Response Body
- Builder for creation of Response
- Something similar for Request
