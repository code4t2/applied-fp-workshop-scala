package marsroverkata

class Version5Tests extends munit.FunSuite {

  import cats.effect._
  import marsroverkata.Version5._

  import scala.Console._

  // TODO: Implement createApplicationFunction
  //  - compose and run the application
  //  - invoke handleResult with application result

  test("go to opposite angle, system test".ignore) {
    val result = execute("RBBLBRF") {
      createApplication("planet.txt", "rover.txt")
    }

    assertEquals(result, s"$GREEN[OK] 4:3:E$RESET")
  }

  test("invalid planet data, system test".ignore) {
    val result = execute("RBBLBRF") {
      createApplication("planet_invalid_data.txt", "rover.txt")
    }

    assertEquals(result, s"$RED[ERROR] InvalidPlanet(ax4,InvalidSize)$RESET")
  }

  test("invalid planet file content, system test".ignore) {
    val result = execute("RBBLBRF") {
      createApplication("planet_invalid_content.txt", "rover.txt")
    }

    assertEquals(result, s"$RED[ERROR] Invalid file content$RESET")
  }
  def execute[A](commands: String)(app: => IO[A]): String = {
    import java.io.{ ByteArrayOutputStream, StringReader }

    val input = new StringReader(commands)
    val out   = new ByteArrayOutputStream
    Console.withIn(input) {
      Console.withOut(out) {
        app.unsafeRunSync()
      }
    }
    out.toString.replace("\r", "").split('\n').last
  }

}
