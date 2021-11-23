package machine

data class CoffeeMachineState(var waterInMachine: Int,
                              var milkInMachine: Int,
                              var beansInMachine: Int,
                              var disposableCups: Int,
                              var cash: Int)

data class Coffee(val water: Int,
                  val milk: Int,
                  val beans: Int,
                  val price: Int)

class CoffeeMachine() {

    // three variants of coffee as per spec
    val espresso = Coffee(250, 0, 16, 4)
    val latte = Coffee(350, 75, 20, 7)
    val cappuccino = Coffee(200, 100, 12, 6)

    // initial state of the machine
    val state = CoffeeMachineState(400, 540, 120, 9, 550)

    var nextAction = ""

    // main loop of the coffee machine
    fun loop() {
        while (nextAction != "exit") {
            print("Write action (buy, fill, take, remaining, exit): ")
            nextAction = readLine()!!
            when (nextAction) {
                "remaining" -> displayState(state)
                "buy" -> {
                    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                    val type = readLine()!!
                    when (type) {
                        "1" -> buy(state, espresso)
                        "2" -> buy(state, latte)
                        "3" -> buy(state, cappuccino)
                    }
                }
                "fill" -> fill(state)
                "take" -> take(state)
            }
        }
    }

    fun displayState(state: CoffeeMachineState) {
        val response = """
            The coffee machine has:
            ${state.waterInMachine} of water
            ${state.milkInMachine} of milk
            ${state.beansInMachine} of coffee beans
            ${state.disposableCups} of disposable cups
            ${'$'}${state.cash} of money
            
        """.trimIndent()
        println(response)
    }

    fun buy(state: CoffeeMachineState, coffee: Coffee) {

        if (check(state, coffee)) {
            state.waterInMachine -= coffee.water
            state.milkInMachine -= coffee.milk
            state.beansInMachine -= coffee.beans
            state.disposableCups -= 1
            state.cash += coffee.price
            println("I have enough resources, making you a coffee!\n")
        } else {
            if (state.waterInMachine < coffee.water) {
                println("Sorry, not enough water!")
            }
            if (state.milkInMachine < coffee.milk) {
                println("Sorry, not enough milk!")
            }
            if (state.beansInMachine < coffee.beans) {
                println("Sorry, not enough water!")
            }
            if (state.disposableCups < 1) {
                println("Sorry, not enough cups!")
            }
            println()
        }
    }

    fun check(state: CoffeeMachineState, coffee: Coffee): Boolean {
        val availability = state.waterInMachine >= coffee.water
                && state.milkInMachine >= coffee.milk
                && state.beansInMachine >= coffee.beans
                && state.disposableCups > 0
        return availability
    }

    fun fill(state: CoffeeMachineState) {
        print("Write how many ml of water do you want to add: ")
        val waterAdded = readLine()!!.toInt()
        print("Write how many ml of milk do you want to add: ")
        val milkAdded = readLine()!!.toInt()
        print("Write how many grams of coffee beans do you want to add: ")
        val beansAdded = readLine()!!.toInt()
        print("Write how many disposable cups of coffee do you want to add: ")
        val cupsAdded = readLine()!!.toInt()
        state.waterInMachine += waterAdded
        state.milkInMachine += milkAdded
        state.beansInMachine += beansAdded
        state.disposableCups += cupsAdded
    }

    fun take(state: CoffeeMachineState) {
        println("I gave you \$${state.cash}\n")
        state.cash = 0
    }
}