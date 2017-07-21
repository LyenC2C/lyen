package par_18_高级类型_todo

/**
  * Created by lyen on 16-10-31.
  */
object Title
object Author
class Document {

  private var title = ""
  private var author = ""
  private var useNextArgAs: Any = null
  def set(obj: Title.type): this.type = { useNextArgAs = obj; this }
  def set(obj: Author.type): this.type = { useNextArgAs = obj; this }
  def to(arg: String): this.type = {
    if (useNextArgAs == Title) title = arg
    if (useNextArgAs == Author) author = arg
    this
  }
  override def toString = getClass.getName + "[title=" + title + ", author=" + author + "]"

}
object Main extends App {
  val book = new Document
  book set Title to "Scala for the Impatient" set Author to "Cay Horstmann"
  println(book)
}