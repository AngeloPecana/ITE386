import java.util.*;

class Task {
    String name;
    long executionTime;

    public Task(String name, long delayInSeconds) {
        this.name = name;
        this.executionTime = System.currentTimeMillis() + (delayInSeconds * 1000);
    }
}

public class TaskScheduler {
    private static final List<Task> tasks = new ArrayList<>();
    private static final Timer timer = new Timer();

    public static void addTask(String name, long delayInSeconds) {
        Task task = new Task(name, delayInSeconds);
        tasks.add(task);
    }

    public static void runScheduler() {
        tasks.sort(Comparator.comparingLong(task -> task.executionTime));

        System.out.println("\nStarting Task Execution...\n");
        
        for (Task task : tasks) {
            long delay = task.executionTime - System.currentTimeMillis();
            if (delay > 0) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Executing Task: " + task.name);
                    }
                }, delay);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter tasks (type 'start' to run the scheduler):");
        
        while (true) {
            System.out.print("Task Name: ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("start")) {
                break;
            }

            System.out.print("Delay (in seconds): ");
            long delay = scanner.nextLong();
            scanner.nextLine();  

            addTask(name, delay);
            System.out.println("Task '" + name + "' added with " + delay + " seconds delay.");
        }

        scanner.close();
        runScheduler();
    }
}
