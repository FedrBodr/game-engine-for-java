package com.gej.util;

import java.util.LinkedList;

public class GTaskManager extends ThreadGroup {
	
	boolean isAlive = false;
	LinkedList<Runnable> taskQueue = null;
	
	int taskId = 0;
	static int managerId = 0;
	
	public GTaskManager(int MaxTasks){
		super("GTaskManager - " + managerId++);
		setDaemon(true);
		isAlive = true;
		taskQueue = new LinkedList<Runnable>();
		for (int i=0; i<MaxTasks; i++){
			new TaskRunner().start();
		}
	}
	
	public GTaskManager(){
		this(100);
	}
	
	public synchronized void runTask(Runnable task){
		if (task!=null){
			taskQueue.add(task);
			notify();
		}
	}
	
	public synchronized Runnable getTask(){
		while (taskQueue.size() == 0){
			if (!isAlive){
				return null;
			}
			try {
				wait();
			} catch (Exception e){}
		}
		return taskQueue.removeFirst();
	}
	
	public synchronized void close(){
		if (isAlive){
			isAlive = false;
			taskQueue.clear();
			interrupt();
		}
	}
	
	public void join(){
		synchronized (this){
			isAlive = false;
			notifyAll();
		}
		Thread[] th = new Thread[activeCount()];
		int count = enumerate(th);
		for (int i=0; i<count; i++){
			try {
				th[i].join();
			} catch (Exception e){}
		}
	}
	
	public void taskStarted(){}
	public void taskStopped(){}
	
	private class TaskRunner extends Thread {
		
		public TaskRunner(){
			super("TaskRunner - " + taskId++);
		}
		
		public void run(){
			taskStarted();
			while(!isInterrupted()){
				Runnable task = null;
				task = getTask();
				if (task!=null){
					try {
						task.run();
					} catch (Exception e){}
				}
			}
			taskStopped();
		}
		
	}

}
