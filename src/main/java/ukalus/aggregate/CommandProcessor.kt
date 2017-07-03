package ukalus.aggregate

import java.lang.IllegalArgumentException


class CommandProcessor<out E>(val aggregate: E, val events: EventStore) {

    init {
        events.load().forEach(this::apply)
    }

    fun handle(command: Any): Any? {
        return when (command) {
            is CreateCommand -> CreateCommandHandler<E>().handle(aggregate, command)
            else -> throw IllegalArgumentException("${command.javaClass.simpleName} not recognized")
        }.apply {
            forEach { apply(it) }
            forEach(events::append)
        }
    }

    private fun apply(event: Any) {
        when (event) {
            is CreatedEvent -> CreatedEventHandler<E>().handle(aggregate, event)
        }
    }
}

data class CreateCommand(val id: String)

interface CommandHandler<in E> {
    fun handle(state: E, command: Any): List<Any>
}

class CreateCommandHandler<in E> : CommandHandler<E> {
    override fun handle(state: E, command: Any): List<Any> {
        return emptyList()
    }
}

interface EventHandler<in E> {
    fun handle(state: E, event: Any)
}

data class CreatedEvent(val id: String)

class CreatedEventHandler<in E> : EventHandler<E> {
    override fun handle(state: E, event: Any) {

    }
}