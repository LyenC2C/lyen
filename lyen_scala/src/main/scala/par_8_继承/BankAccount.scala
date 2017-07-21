package par_8_继承

/**
  * Created by lyen on 16-10-27.
  */
class BankAccount(initialBalance: Double) {

  private var balance = initialBalance

  def deposit(amount: Double) = {
    balance += amount; balance
  }
  def withDraw(amount: Double) = {
    balance -= amount
    balance
  }
}
