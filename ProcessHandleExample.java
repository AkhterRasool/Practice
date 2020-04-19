import java.util.Date;
import java.time.Duration;
import java.util.Optional;

public class ProcessHandleExample {

	public static void main(String[] args) {
		int argsCount = args.length;
		switch(argsCount) {
			case 0: describeProgramStartCommand(); break;
			case 1: captureProcessInformation(Integer.parseInt(args[0])); break;
			default: System.out.println("Only 1 argument is accepted."); describeProgramStartCommand();
		}	
	}

	private static void describeProgramStartCommand() {
		System.out.println("Please execute the program as follows: ");		
		System.out.println("java Practice [-pid]");
		System.out.println("Ex: java Practice 1233");
		System.out.println("Where 1233 is the process id");
		System.out.println("Check your CPU Activity Monitor / Task Manager to view processes with their ids.");
	}

	private static void captureProcessInformation(int pid) {
		ProcessHandle.of(pid)
			.map(ProcessHandle::info)
			.ifPresentOrElse(ProcessHandleExample::printProcessInfo, () -> System.out.println("Could not find process with id '" + pid + "'."));
	}

	private static void printProcessInfo(ProcessHandle.Info info) {
		String command = info.command().orElse("No Command");
		String commandLine = info.commandLine().orElse("No Command Line");
		System.out.println("Command: " + command);	
		System.out.println("Command Line: " + command);	
		String startDate = info.totalCpuDuration()
			.map(Duration::toMillis)
			.map(Date::new)
			.map(Date::toString)
			.orElse("Could not determine the start date");

		System.out.println("Start Date: " + startDate);
			
	}
}
