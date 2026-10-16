class Main{
    public static void searchInSortedArr(int [] arr, int target){
        // finding the sorted side- 
        int n = arr.length;
        int start = 0;
        int end = n-1;
        int mid = start + (end - start)/2;
        if(arr[start] <= arr[mid]){
            for(int i=0; i<mid; i++){
                System.out.println(arr[i]);
            }
        }
        else{
            for(int i=mid+1; i<n; i++){
                System.out.println(arr[i]);
            }
        }
    }
    public static void main(String [] args){
        int [] arr = {5, 6, 7, 8, 9, 0, 2, 3, 4};
        searchInSortedArr(arr, 5);
    }
}