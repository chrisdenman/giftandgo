import io.gatling.javaapi.core.CoreDsl.StringBody
import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.core.CoreDsl.rampUsers
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import io.netty.handler.codec.http.HttpResponseStatus.CREATED
import java.util.UUID.randomUUID

@Suppress("unused")
class PostSimulation : Simulation() {

    companion object {
        val personSpeedRecord: String
            get() =
                listOf(
                    randomUUID(),
                    randomUUID().toString().substring(0..5),
                    randomUUID(),
                    randomUUID(),
                    randomUUID(),
                    1.1,
                    3.2
                )
                    .joinToString("|")
    }

    private val postData = exec(
        http("Create People Speed Data")
            .post("/peopleSpeedData")
            .body(StringBody(personSpeedRecord))
            .check(status().shouldBe(CREATED.code()))
    )

    private val httpProtocol =
        http
            .baseUrl("http://127.0.0.1:8080")
            .contentTypeHeader("text/plain")
            .header("x-forwarded-for", "google.co.uk")

    private val postingUsers =
        scenario("Posting Data")
            .exec(postData)

    init {
        setUp(
            postingUsers
                .injectOpen(
                    rampUsers(2)
                        .during(10)
                )
        )
            .protocols(httpProtocol)
    }
}
