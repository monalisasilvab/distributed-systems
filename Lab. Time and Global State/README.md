# Time and Global State Laboratory

Prerequisites: Communication with TCP/UDP Sockets in Java, Threads, notions of concurrency.

## Goals
- Implement and observe the difference between physical and logical clocks.
- Simulate Lamport's logical clock algorithm.
- Implement vector clocks.

## Practice Activies

### Part 1
Create three processes (threads or separate programs) that exchange messages with varying sleep() times.
- Each process maintains its own local physical clock (System.currentTimeMillis())
- Each event (send or receive) must be logged with the physical timestamp.
Objective: To demonstrate that the order of events can appear incorrect without logical control.

### Part 2
Each process maintains a counter L(p), starting at 0.
- Increments L(p) with each internal event.
- When sending a message, sends the value of L(p).
- When receiving, L(p) = max(L(received), L(p)) + 1
Objective: Observe causal consistency between events.

### Part 3
Each process has a vector V[i] of size N (number of processes).
- Local event: V[i][i]++
- Message sending: sends a copy of V[i]
- Reception: V[i][j] = max(V[i][j], Vmsg[j]) for all j, then V[i][i]++
Objective: Determine concurrency between events using vectors.