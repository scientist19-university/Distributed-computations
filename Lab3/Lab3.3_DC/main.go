package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Resourse is a ....
type Resourse struct {
	res int
	mux sync.Mutex
}

func smokerPaper(cigaretteIsDone chan bool, r *Resourse) {

	for {
		r.mux.Lock()
		if r.res == 1 {
			time.Sleep(time.Second)
			fmt.Println("Smoker with paper is smoking...")
			r.res = -1
			cigaretteIsDone <- true
		}
		r.mux.Unlock()
	}
}

func smokerMatches(cigaretteIsDone chan bool, r *Resourse) {

	for {
		r.mux.Lock()
		if r.res == 2 {
			time.Sleep(time.Second)
			fmt.Println("Smoker with matches is smoking...")
			r.res = -1
			cigaretteIsDone <- true
		}
		r.mux.Unlock()
	}
}

func smokerTobacco(cigaretteIsDone chan bool, r *Resourse) {

	for {
		r.mux.Lock()
		if r.res == 0 {
			time.Sleep(time.Second)
			fmt.Println("Smoker with tobacco is smoking...")
			r.res = -1
			cigaretteIsDone <- true
		}
		r.mux.Unlock()
	}
}

func dealer(cigaretteIsDone chan bool, r *Resourse) {

	for {
		r.mux.Lock()
		switch i := rand.Intn(3); i {
		case 0:
			r.res = i
			fmt.Println("Dealer has put matches and paper")
		case 1:
			r.res = i
			fmt.Println("Dealer has put matches and tobacco")
		case 2:
			r.res = i
			fmt.Println("Dealer has put tobacco and paper")
		}
		r.mux.Unlock()
		<-cigaretteIsDone
	}
}

func main() {

	cigaretteDone := make(chan bool)
	done := make(chan bool)
	resourse := &Resourse{}

	go smokerTobacco(cigaretteDone, resourse)
	go smokerPaper(cigaretteDone, resourse)
	go smokerMatches(cigaretteDone, resourse)
	go dealer(cigaretteDone, resourse)
	<-done
}
