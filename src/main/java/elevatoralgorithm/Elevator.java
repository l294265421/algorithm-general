package elevatoralgorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Elevator {
	// 电梯初始状态是停止（也就是最初没有运动）
	private State state = State.STATIC;
	// 电梯运动的方向（电梯静止的时候没有方向）
	private Direction direction;
	// 电梯所处的位置，最开始在0楼
	private int position = 0;
	// 电梯能去的最低楼层
	private int minPosition;
	// 电梯能去的最高楼层
	private int maxPosition;
	// 每一楼层接收到的命令
	private List<List<Instruction>> instructionsForEachPosition = new LinkedList<List<Instruction>>();
	// 电梯正在执行的命令（当发送命令的那些人进入电梯时，相应的，它们的命令也就开始被执行了）
	private List<Instruction> instructionInElevator = new LinkedList<Instruction>();
	// 还有多少命令没有执行完
	private int instructionNumNeedToExec = 0;
	// 电梯静止时，收到的第一条命令，该命令使电梯跑起来
	private Instruction firstInstruction = null;

	public Elevator(int minPosition, int maxPosition) {
		super();
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
		int positionNum = maxPosition - minPosition;
		for (int i = 0; i < positionNum; i++) {
			instructionsForEachPosition.add(new LinkedList<Instruction>());
		}
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getMinPosition() {
		return minPosition;
	}

	public void setMinPosition(int minPosition) {
		this.minPosition = minPosition;
	}

	public int getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

	public List<List<Instruction>> getInstructionsForEachPosition() {
		return instructionsForEachPosition;
	}

	public void setInstructionsForEachPosition(
			List<List<Instruction>> instructionsForEachPosition) {
		this.instructionsForEachPosition = instructionsForEachPosition;
	}

	public List<Instruction> getInstructionInElevator() {
		return instructionInElevator;
	}

	public void setInstructionInElevator(List<Instruction> instructionInElevator) {
		this.instructionInElevator = instructionInElevator;
	}

	public int getInstructionNumNeedToExec() {
		return instructionNumNeedToExec;
	}

	public void setInstructionNumNeedToExec(int instructionNumNeedToExec) {
		this.instructionNumNeedToExec = instructionNumNeedToExec;
	}

	public Instruction getFirstInstruction() {
		return firstInstruction;
	}

	public void setFirstInstruction(Instruction firstInstruction) {
		this.firstInstruction = firstInstruction;
	}

	@Override
	public String toString() {
		return "Elevator [state=" + state + ", direction=" + direction
				+ ", position=" + position + ", minPosition=" + minPosition
				+ ", maxPosition=" + maxPosition
				+ ", instructionsForEachPosition="
				+ instructionsForEachPosition + ", instructionInElevator="
				+ instructionInElevator + ", instructionNumNeedToExec="
				+ instructionNumNeedToExec + ", firstInstruction="
				+ firstInstruction + "]";
	}

	/**
	 * 启动一个电梯
	 */
	public void start() {
		Container container = new Container(this);
		Thread containerThread = new Thread(container);
		containerThread.start();

		InstructionReceiver instructionReceiver = new InstructionReceiver(this);
		Thread instructionReceiverThread = new Thread(instructionReceiver);
		instructionReceiverThread.start();
	}

	/**
	 * 电梯——装人的柜子;
	 * 跑到某一层，并且要继续移动的柜子改变方面的时刻：
	 * （1）到达顶楼或底楼时
	 * （2）当前楼层没有要前进的人并且前方没有要坐电梯的人
	 * 
	 * @author yuncong
	 *
	 */
	private static class Container implements Runnable {
		private Elevator elevator;

		public Container(Elevator elevator) {
			super();
			this.elevator = elevator;
		}

		@Override
		public void run() {

			do {
				// 本层是否开门
				boolean open = false;

				// 箱子在这一层停下来了
				if (elevator.state == State.STATIC) {
					while (elevator.instructionNumNeedToExec == 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// firstInstruction让elevator动起来，并决定了elevator的方向
					elevator.state = State.RUNNING;
					if (elevator.firstInstruction.getOrigion() > elevator.position) {
						elevator.direction = Direction.UP;
					} else if (elevator.firstInstruction.getOrigion() < elevator.position) {
						elevator.direction = Direction.DOWN;
					} else {
						elevator.direction = elevator.firstInstruction
								.getDirection();
						open = true;
					}
					// 箱子跑到了这一层，还要继续跑
				} else {
					// 看有没有到当前楼层的
					Iterator<Instruction> instructionInElevatorIterator = elevator.instructionInElevator
							.iterator();
					while (instructionInElevatorIterator.hasNext()) {
						Instruction temp = instructionInElevatorIterator.next();
						if (temp.getDestination() == elevator.position) {
							open = true;
							instructionInElevatorIterator.remove();
							elevator.instructionNumNeedToExec--;
						}
					}

					// 确定电梯移动方向
					if (elevator.position == elevator.maxPosition) {
						elevator.setDirection(Direction.DOWN);
					} else if (elevator.position == elevator.minPosition) {
						elevator.setDirection(Direction.UP);
					}

					// 寻找从当前楼层出发且和电梯方向一致的people
					List<Instruction> currentFloorInstructions = elevator.instructionsForEachPosition
							.get(elevator.getPosition());
					Iterator<Instruction> currentFloorInstructionsIterator = currentFloorInstructions
							.iterator();
					while (currentFloorInstructionsIterator.hasNext()) {
						Instruction temp = currentFloorInstructionsIterator
								.next();
						if (temp.getDirection() == elevator.getDirection()) {
							elevator.instructionInElevator.add(temp);
							currentFloorInstructionsIterator.remove();
							open = true;
						}
					}

					// 查看前方是否有要坐电梯的
					boolean thereHavePeople = false;
					if (elevator.instructionInElevator.size() == 0) {
						if (elevator.direction == Direction.UP) {
							for (int i = elevator.position + 1; i < elevator.maxPosition; i++) {
								if (elevator.instructionsForEachPosition.get(i)
										.size() > 0) {
									thereHavePeople = true;
									break;
								}
							}
						} else {
							for (int i = elevator.position - 1; i >= elevator.minPosition; i--) {
								if (elevator.instructionsForEachPosition.get(i)
										.size() > 0) {
									thereHavePeople = true;
									break;
								}
							}
						}
					}

					// 如果没有人往电梯前进的方向去，就改变电梯行进方向，并在这一层找人
					if (elevator.instructionInElevator.size() == 0
							&& !thereHavePeople) {
						if (elevator.direction == Direction.UP) {
							elevator.direction = Direction.DOWN;
						} else {
							elevator.direction = Direction.UP;
						}

						// 寻找从当前楼层出发且和电梯方向一致的people
						currentFloorInstructions = elevator.instructionsForEachPosition
								.get(elevator.getPosition());
						currentFloorInstructionsIterator = currentFloorInstructions
								.iterator();
						while (currentFloorInstructionsIterator.hasNext()) {
							Instruction temp = currentFloorInstructionsIterator
									.next();
							if (temp.getDirection() == elevator
									.getDirection()) {
								if ((temp.getDirection() == Direction.UP && temp
										.getDestination() > elevator
										.getPosition())
										|| (temp.getDirection() == Direction.DOWN && temp
												.getDestination() < elevator
												.getPosition())) {
									elevator.instructionInElevator
											.add(temp);
									currentFloorInstructionsIterator
											.remove();
									open = true;
								}
							}
						}
					}
				}

				if (open) {
					elevator.openDoor();
				}

				// 电梯移动
				if (elevator.getDirection() == Direction.UP) {
					elevator.position++;
				} else {
					elevator.position--;
				}
				// 电梯移动一层楼需要的时间
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 根据instructionNumNeedToExec判断是继续running还是停下来
				if (elevator.instructionNumNeedToExec == 0) {
					elevator.state = State.STATIC;
				}
			} while (true);

		}

	}

	/**
	 * 电梯——命令接收器
	 * 
	 * @author yuncong
	 *
	 */
	private static class InstructionReceiver implements Runnable {
		private Elevator elevator;

		public InstructionReceiver(Elevator elevator) {
			super();
			this.elevator = elevator;
		}

		@Override
		public void run() {
			System.out.println("---------------------");
			System.out.println("请输入命令，");
			System.out.println("如果你在2楼，想去3楼，输入：2 3 up");
			System.out.println("如果你在9楼，想去2楼，输入：9 2 down");
			System.out.println("---------------------");
			while (true) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
				System.out.println(line);
				String[] instructionPart = line.split("\\s");
				if (instructionPart.length != 3) {
					continue;
				}
				int origion = Integer.parseInt(instructionPart[0]);
				int destination = Integer.parseInt(instructionPart[1]);
				Direction direction;
				if (instructionPart[2].equals("up")) {
					direction = Direction.UP;
				} else {
					direction = Direction.DOWN;
				}
				Instruction instruction = new Instruction(origion, destination,
						direction);
				;
				receive(instruction);
			}
		}

		private boolean receive(Instruction instruction) {
			if ((instruction.getOrigion() == elevator.maxPosition && instruction
					.getDirection() == Direction.UP)
					|| (instruction.getOrigion() == elevator.minPosition && instruction
							.getDirection() == Direction.DOWN)) {
				return false;
			}

			int origion = instruction.getOrigion();
			List<Instruction> instructions = elevator.instructionsForEachPosition
					.get(origion);
			// 就像你和同学要去上同一节课，要坐电梯上楼，如果你同学按了按钮，你就不用按了
			if (instructions.contains(instruction)) {
				return false;
			} else {
				elevator.instructionNumNeedToExec++;
				if (elevator.instructionNumNeedToExec == 1) {
					elevator.firstInstruction = instruction;
				}
				instructions.add(instruction);
				return true;
			}
		}

	}

	public void openDoor() {
		try {
			Thread.sleep(3000);
			System.out.println(this.position
					+ " floor's door is opened, please come in");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Elevator elevator = new Elevator(0, 10);
		elevator.start();
	}
}
