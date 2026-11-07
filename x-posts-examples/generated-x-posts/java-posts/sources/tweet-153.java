// ❌ Manual bounds check — easy to get the condition wrong
T get(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("index=" + index);
    }
    return elementData[index];
}

// ✅ Objects.checkIndex — one call, standard exception
T get(int index) {
    Objects.checkIndex(index, size); // 0 <= index < size
    return elementData[index];
}
