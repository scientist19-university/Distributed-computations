package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Barrier struct {
	cur    int
	max    int
	mux    sync.Mutex
	before chan int
	after  chan int
}

func New(n int) *Barrier {
	b := Barrier{
		max:    n,
		before: make(chan int, n),
		after:  make(chan int, n),
	}
	return &b
}

func (b *Barrier) Before() {
	b.mux.Lock()
	b.cur++
	if b.cur == b.max {
		// open 2nd gate
		for i := 0; i < b.max; i++ {
			b.before <- 1
		}
	}
	b.mux.Unlock()
	<-b.before
}

func (b *Barrier) After() {
	b.mux.Lock()
	b.cur--
	if b.cur == 0 {
		// open 1st gate
		for i := 0; i < b.max; i++ {
			b.after <- 1
		}
	}
	b.mux.Unlock()
	<-b.after
}

func changeAndCheckSums(nums *[size]int, b *Barrier) {

	for {
		operation := r.Intn(2)
		var sum = 0
		for i := 0; i < size; i++ {
			sum += nums[i]
		}
		if sum > 10 {
			operation = 0
		}
		if sum < -10 {
			operation = 1
		}
		index1 := r.Intn(size)

		if operation == 0 {
			nums[index1]--
		} else {
			nums[index1]++
		}

		b.Before()
		var sum1, sum2, sum3 int = 0, 0, 0
		for i := 0; i < size; i++ {
			sum1 += numbers1[i]
			sum2 += numbers2[i]
			sum3 += numbers3[i]
		}
		b.After()

		if sum1 == sum2 && sum2 == sum3 {
			fmt.Printf("Sums %d,%d,%d are equal\n", sum1, sum2, sum3)
			break
		} else {
			fmt.Printf("Sums %d,%d,%d aren`t equal\n", sum1, sum2, sum3)
		}
	}
}

const size = 3

var numbers1 = [size]int{1, 2, 3}
var numbers2 = [size]int{2, 2, 3}
var numbers3 = [size]int{1, 0, 3}
var r = rand.New(rand.NewSource(55))

func main() {

	b := New(3)

	go changeAndCheckSums(&numbers1, b)
	go changeAndCheckSums(&numbers2, b)
	go changeAndCheckSums(&numbers3, b)

	<-time.After(time.Second * 7)
}
