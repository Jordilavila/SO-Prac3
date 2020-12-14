#SO-Prac3

_Third practice of Operative Systems subject in Second Course of Computers Engineering Degree in Universitat d'Alacant. Year 2020._

## Introduction

I need to create a functional memory manager simulator. This simulator implements two of four process-adding algorithms to the system primary memory. 

I've created a different groups of objects:

 - model: Principal objects
 - model.exceptions: All the exceptions
 - model.io: In/Out objects
 
## How works?

MemoryPractice is the object that works as a memory manager. Once the object that controls the program is built, we will call MemoryManager.run() method.
This method is the one that will load the processes to the Processor.queue and will run them. If it can't move them, it will capture the exception and let the iteration pass and it will try to do it in the next iteration.
Anyway, in each iteration the processor will increment the internal counter of each processes that are in execution.
Finally, if the internal counter of the process is equals to executionTime, the process will be killed.
