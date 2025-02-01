package net.delugan.teachly.utils.plugin;

public interface TeachlyPluginInterface {
    /**
     * Log a message with the INFO level.
     *
     * @param msg The message to log.
     */
    void info(Object msg);

    /**
     * Log a message with the WARN level.
     *
     * @param msg The message to log.
     */
    void warn(Object msg);

    /**
     * Log a message with the ERROR level.
     *
     * @param msg The message to log.
     */
    void error(Object msg);

    /**
     * Execute an exercise.
     *
     * @param player   The player that will execute the exercise.
     * @param exercise The exercise to execute.
     * @return A {@link Result} indicating the success of the operation.
     */
     Result<Void> exercise(Object player, String exercise);

    /**
     * Send a message to a player.
     *
     * @param msg The message to send.
     * @param dst The player to send the message to.
     * @return A {@link Result} indicating the success of the operation.
     */
     Result<Void> sendMessage(String msg, Object dst);

    /**
     * Broadcast a message to all players.
     *
     * @param msg The message to broadcast.
     * @return A {@link Result} indicating the success of the operation.
     */
     Result<Void> broadcast(String msg);

    /**
     * Register a new event listener.
     *
     * @param event    The name of the event class to listen to.
     * @param callback The name of the callback function that will be called when the event is triggered.
     * @param priority The priority of the event listener. Valid values are found in {@link EventPriority}
     * @return A {@link Result} indicating the success of the operation.
     */
     Result<Void> subscribe(String event, String callback, String priority);

    /**
     * Register a new event listener with NORMAL priority.
     *
     * @param event    The name of the event class to listen to.
     * @param callback The name of the callback function that will be called when the event is triggered.
     * @return A {@link Result} indicating the success of the operation.
     */
     Result<Void> subscribe(String event, String callback);
}
