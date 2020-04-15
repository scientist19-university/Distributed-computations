package main

import (
	"fmt"
	"math/rand"
	"strconv"
)

func main() {

	const priceBound = 1000
	const priceDiscrete = 10
	const n = 100

	var warehouse [n]string

	for i := 0; i < n; i++ {
		warehouse[i] = "item " + strconv.Itoa(i)
	}

	itemsToLoad := make(chan string, 10)
	itemsToEstimate := make(chan string, 10)
	done := make(chan bool)

	go func(warehouse [n]string, itemsToLoad chan string) {
		for _, item := range warehouse {
			fmt.Println("Ivanov confiscated", item)
			itemsToLoad <- item
		}
	}(warehouse, itemsToLoad)

	go func(itemsToLoad chan string, itemsToEstimate chan string) {
		for i := 0; i < n; i++ {
			item := <-itemsToLoad
			fmt.Println("Petrov loaded", item)
			itemsToEstimate <- item
		}
	}(itemsToLoad, itemsToEstimate)

	go func(itemsToEstimate chan string, done chan bool) {
		for i := 0; i < n; i++ {
			item := <-itemsToEstimate
			fmt.Println("Necheporuk estimated", item, "cost is", strconv.Itoa(rand.Intn(priceBound/priceDiscrete)*priceDiscrete))
		}
		done <- true
	}(itemsToEstimate, done)

	<-done
}
