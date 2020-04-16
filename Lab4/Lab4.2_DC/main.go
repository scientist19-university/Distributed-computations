package main

import (
	"bufio"
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"sync"
	"time"
)

var seed = rand.NewSource(time.Now().UnixNano())
var random = rand.New(seed)

func gardener(N int, garden [][]int, mutex *sync.RWMutex) {
	for {
		time.Sleep(time.Second)

		for i := 0; i < N; i++ {
			for j := 0; j < N; j++ {
				mutex.Lock()
				if garden[i][j] != 5 {
					garden[i][j]++
				}
				mutex.Unlock()
			}
		}
	}
}

func nature(N int, garden [][]int, mutex *sync.RWMutex) {
	for {
		time.Sleep(time.Second)

		for k := 0; k < 3; k++ {
			i := random.Intn(N)
			j := random.Intn(N)

			mutex.Lock()
			garden[i][j] = random.Intn(5)
			mutex.Unlock()
		}
	}
}

func printOnScreen(N int, garden [][]int, mutex *sync.RWMutex) {
	for {
		mutex.RLock()
		for i := 0; i < N; i++ {
			for j := 0; j < N; j++ {
				fmt.Print(garden[i][j], " ")
			}
			fmt.Println()
		}
		mutex.RUnlock()

		fmt.Println()
		time.Sleep(time.Second * 3)
	}
}

func printToFile(N int, garden [][]int, mutex *sync.RWMutex) {
	for {
		mutex.RLock()
		s := ""
		for i := 0; i < N; i++ {
			for j := 0; j < N; j++ {
				s += strconv.Itoa(garden[i][j]) + " "
			}
			s += "\n"
		}

		f, _ := os.OpenFile("output.txt", os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
		writeDataIntoFile(f, s)
		mutex.RUnlock()

		time.Sleep(time.Second * 3)
	}
}

func writeDataIntoFile(f *os.File, data string) {
	w := bufio.NewWriter(f)
	_, _ = fmt.Fprintln(w, data)
	_ = w.Flush()
}

func main() {

	done := make(chan int)
	sizeOfGarden := 5
	garden := make([][]int, sizeOfGarden, sizeOfGarden)

	mutex := &sync.RWMutex{}

	for i := 0; i < sizeOfGarden; i++ {
		garden[i] = make([]int, sizeOfGarden, sizeOfGarden)
		for j := 0; j < sizeOfGarden; j++ {
			garden[i][j] = random.Intn(5)
		}
	}

	_, _ = os.Create("output.txt")

	go gardener(sizeOfGarden, garden, mutex)
	go nature(sizeOfGarden, garden, mutex)
	go printOnScreen(sizeOfGarden, garden, mutex)
	go printToFile(sizeOfGarden, garden, mutex)

	<-done
}
