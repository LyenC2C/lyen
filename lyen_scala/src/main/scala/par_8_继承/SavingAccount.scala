package par_8_继承

/**
  * Created by lyen on 16-10-27.
  */
class SavingAccount(initialBalance: Double) extends BankAccount(initialBalance: Double) {

  private var num: Int = _

  def earnMonthlyInterest() = {
    num = 3
    super.deposit(1D)
  }

  override def deposit(amount: Double): Double = {
    num -= 1
    if (num < 0) super.deposit(amount - 1) else super.deposit(amount)
  }

  override def withDraw(amount: Double): Double = {
    num -= 1
    if (num < 0) super.withDraw(amount - 1) else super.withDraw(amount)
  }
}
