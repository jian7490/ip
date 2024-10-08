package chatbaby;

/**
 * Represents a command to find tasks that match a certain keyword.
 * The keyword is extracted from the command body and used to search through the task list.
 */
public class FindCommand extends Command {
    private static final int BEGIN_INDEX = 5;

    /**
     * Constructs a FindCommand with the specified command body.
     *
     * @param commandBody The body of the command, which includes the keyword to search for.
     */
    public FindCommand(String commandBody) {
        super(commandBody);
    }

    /**
     * Executes the find command. Searches through the task list for tasks that contain
     * the keyword specified in the command body. Displays the matching tasks if found,
     * or an appropriate message if no matches are found.
     *
     * @param tasks   The list of tasks to search through.
     * @param ui      The UI object to interact with the user (not used in this implementation).
     * @param storage The storage object to save any changes (not used in this implementation).
     * @throws ChatBabyException If the search keyword is empty.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChatBabyException {
        if (commandBody.length() <= BEGIN_INDEX) {
            throw new ChatBabyException("Oh no!!! The task description cannot be empty.");
        }
        try {
            String target = commandBody.substring(BEGIN_INDEX).trim();
            TaskList matchedList = findTasks(tasks, target);
            boolean hasMatch = matchedList.hasTask();
            if (!hasMatch) {
                System.out.println("There is no task that matches.");
            } else {
                matchedList.listTasks();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBabyException("Oh no!!! The search keyword cannot be empty!");
        }
    }

    private TaskList findTasks(TaskList tasks, String target) {
        TaskList matchedList = new TaskList();
        for (Task task : tasks.getTasks()) {
            if (task.getName().contains(target)) {
                matchedList.addTask(task);
            }
        }
        return matchedList;
    }
}
