package ukalus.aggregate

import java.lang.IllegalArgumentException


class CommandProcessor(val aggregate: Any, val events: EventStore) {

    init {
        events.load().forEach(this::apply)
    }

    fun handle(command: Any): Any? {
        return when (command) {
            is CreateCommand -> createCommandHandler(aggregate, command)
            else -> throw IllegalArgumentException("${command.javaClass.simpleName} not recognized")
        }.apply {
            forEach { apply(it) }
            forEach(events::append)
        }
    }

    private fun apply(event: Any) {
        when (event) {
            is CreatedEvent -> createdEventHandler(aggregate, event)
        }
    }
}

data class CreateCommand(val id: String)

fun createCommandHandler(state: Any, command: CreateCommand): List<Any> {
    return emptyList()
}

data class CreatedEvent(val id: String)

fun createdEventHandler(state: Any, event: CreatedEvent) {

}
