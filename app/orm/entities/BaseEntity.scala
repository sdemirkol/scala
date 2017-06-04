package orm.entities

import slick.driver.MySQLDriver.api._
import slick.driver.JdbcProfile
import scala.reflect._
import slick.lifted.ProvenShape
import java.sql.Timestamp
import org.joda.time.DateTime

abstract class BaseEntity {
  def ID: Long = 0
  val InsertDate: DateTime = DateTime.now()
  val UpdateDate: DateTime = DateTime.now()
  //def IsDeleted: Boolean = false
}

abstract class BaseTable[E <: BaseEntity: ClassTag](tag: Tag, tableName: String)
    extends Table[E](tag, tableName) {
  implicit val JodaDateTimeMapper = MappedColumnType.base[DateTime, Timestamp](
    dt => new Timestamp(dt.getMillis),
    ts => new DateTime(ts.getTime()))
  val classOfEntity = classTag[E].runtimeClass
  val ID: Rep[Long] = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  val InsertDate: Rep[DateTime] = column[DateTime]("InsertDate")
  val UpdateDate: Rep[DateTime] = column[DateTime]("UpdateDate")
}