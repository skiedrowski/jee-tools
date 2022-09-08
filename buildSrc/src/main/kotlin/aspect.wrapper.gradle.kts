import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.named

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.5.1"
    distributionType = Wrapper.DistributionType.ALL
}
