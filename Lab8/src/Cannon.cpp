#include "pch.h"
#include < stdio.h >
#include < stdlib.h >
#include < time.h >
#include < math.h >
#include < mpi.h >
#include < locale >
#include < iostream >

using namespace std;

int ProcNum = 0;     
int ProcRank = 0;    
int GridSize;        
int Coord[2];    	 
MPI_Comm Grid;   	 
MPI_Comm Col;     	 
MPI_Comm Row;     	 

void CreateCommunicators()
{
	int dims[2];
	int periods[2];
	int subdims[2];
	periods[0] = 0;
	periods[1] = 0;
	dims[0] = GridSize;
	dims[1] = GridSize;

	MPI_Cart_create(MPI_COMM_WORLD, 2, dims, periods, 2, &Grid); 
	MPI_Cart_coords(Grid, ProcRank, 2, Coord);
	subdims[0] = 1;
	subdims[1] = 0;
	MPI_Cart_sub(Grid, subdims, &Col);
	subdims[0] = 0;
	subdims[1] = 1;
	MPI_Cart_sub(Grid, subdims, &Row);
}
void MemoryAllocation(double* &A, double* &B, double* &C, double* &BlockA, double* &BlockB, double* &BlockC, int &Size, int &BlockSize)
{
	if (ProcRank == 0)
	{
		do
		{
			cout << "\nEnter size of the initial objects: ";
			cin >> Size;
			if (Size%GridSize != 0)
			{
				cout << "Size of matricies must be divisible by the grid size!\n";
			}
		{
		ATransit(BlockA, BlockSize);
		}
				if ((Coord[1] != 0) && (Coord[1] > i))
				{
					BTransit(BlockB, BlockSize);
				}
			}
		}

void MatrixMult(double* A, double* B, double* &C, int Size)
{
	for (int i = 0; i < Size; i++)
		for (int j = 0; j < Size; j++)
			for (int k = 0; k < Size; k++)
				C[i*Size + j] += A[i*Size + k] * B[k*Size + j];
}


void Calculation(double* BlockA, double* BlockB, double* BlockC, int BlockSize)
{
	for (int i = 0; i < GridSize; i++)
	{
		MatrixMult(BlockA, BlockB, BlockC, BlockSize);
		ATransit(BlockA, BlockSize);
		BTransit(BlockB, BlockSize);
	}
}
		
void Result(double* C, double* BlockC, int Size, int BlockSize)
{
	double * Buff = new double[Size*BlockSize];
	for (int i = 0; i < BlockSize; i++)
	{
		MPI_Gather(&BlockC[i*BlockSize], BlockSize, MPI_DOUBLE, &Buff[i*Size], BlockSize, MPI_DOUBLE, 0, Row);
	}
	if (Coord[1] == 0)
	{
		MPI_Gather(Buff, BlockSize*Size, MPI_DOUBLE, C, BlockSize*Size, MPI_DOUBLE, 0, Col);
	}
	delete[] Buff;
}

void Print(double* Matrix, int Size)
{
	for (int i = 0; i < Size; i++)
	{
		for (int j = 0; j < Size; j++)
			if (j == Size - 1)
				cout << Matrix[i*Size + j] << "\n";
			else cout << Matrix[i*Size + j] << " ";
	}
}

void Clear(double* A, double* B, double* C, double* BlockA, double* BlockB, double* BlockC)
{
	if (ProcRank == 0)
	{
		delete[] A;
		delete[] B;
		delete[] C;
	}
	delete[] BlockA;
	delete[] BlockB;
	delete[] BlockC;
}

void main(int argc, char* argv[])
{
	double* A;  
	double* B;  
	double* C;  
	int Size;   
	int BlockSize;    
	double* BlockA;   
	double* BlockB;   
	double* BlockC;   
	clock_t Start, med, End;
	double Finish;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &ProcNum);
	MPI_Comm_rank(MPI_COMM_WORLD, &ProcRank);
	GridSize = sqrt((double)ProcNum);
	if (ProcNum != GridSize * GridSize)
	{
		if (ProcRank == 0)
		{
			cout << "Number of processes must be a perfect square \n" << endl;
		}
	}
	else
	{
		if (ProcRank == 0)
		{
			cout << "Cannon algorithm: " << endl;
		}
		CreateCommunicators();	
		MemoryAllocation(A, B, C, BlockA, BlockB, BlockC, Size, BlockSize);	
		if (ProcRank == 0) Start = clock();
		DataDistribution(A, B, BlockB, BlockA, Size, BlockSize);
		MPI_Barrier(Grid);
		Calculation(BlockA, BlockB, BlockC, BlockSize);
		Result(C, BlockC, Size, BlockSize);
		MPI_Barrier(Grid);
		if (ProcRank == 0)
		{
			End = clock();
			Finish = (double)(End - Start) / CLOCKS_PER_SEC;
		}
		if (ProcRank == 0)
		{
			if (Size < 8)
			{
				cout << "Matrix À:" << endl;
				Print(A, Size);
				cout << "Matrix Â:" << endl;
				Print(B, Size);
				cout << "Matrix Ñ:" << endl;
				Print(C, Size);
				cout << "Time: " << Finish << endl;
			}
		}
		Clear(A, B, C, BlockA, BlockB, BlockC); 
	}
	MPI_Finalize();
}