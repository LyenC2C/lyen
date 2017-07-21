package par_8_继承

/**
  * Created by lyen on 16-10-27.
  */
class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance: Double){

  override def deposit(amount: Double): Double = super.deposit(amount - 1d)

  override def withDraw(amount: Double): Double = super.withDraw(amount + 1d)

}
