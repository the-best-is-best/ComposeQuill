import ktor.ktor_handle_errors.Failure
import ktor.ktor_handle_errors.KtorErrorHandler
import ktor.ktor_services.KtorServices

class CreateOrderRepository {
    suspend fun invoke(apiKey: String): Either<Failure, GoogleFonts> {
        return try {
            val result = KtorServices().getGoogleFonts(apiKey)
            return Either.Right(result)

        } catch (e: Exception) {
            Either.Left(KtorErrorHandler().handle(e))
        }
    }
}