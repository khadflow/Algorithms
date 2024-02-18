#
#   Implementation of several Dynamic Programming Problems using
# 	Memoization and Tabulation with test cases.
#   
#	@author Khadijah Flowers, khadijah20flowers@gmail.com
#

# Given a target and an array of numbers
# return one set of values in numbers that sum to the target
# if it exists. return None otherwise.
def howSum(target, numbers, memo={}):
	if target in memo:
		return memo[target]
	if (target == 0):
		return []
	if (target < 0):
		return None
	for num in numbers:
		result = howSum(target - num, numbers, memo)
		if result != None:
			result += [num]
			memo[target] = result
			return result
	memo[target] = None
	return None

#print(howSum(7, [2, 3], {})) # 2, 2, 3
#print(howSum(7, [5, 3, 4, 7], {})) # 4, 3
#print(howSum(7, [2, 4], {})) # None
#print(howSum(300, [7, 14], {})) # None


# A decision Problem
# m = target
# n = array size
# Runtime: O(n^m * m) -> without DP, Space: O(m^2)
# Runtime: O(n * m^2) -> with DP, Space: O(m^2)
def canConstruct(target, array, memo={}):
	if target in memo:
		return memo[target]
	if (target == ''):
		return True
	else:
		for word in array:
			if (len(word) <= len(target)):
				if (word == target[:len(word)]):
					canBuild = canConstruct(target[len(word):], array, memo)
					if (canBuild):
						memo[target] = True
						return canBuild
	memo[target] = False
	return False

#print(canConstruct('abcdef', ['ab', 'abc', 'cd', 'def', 'abcd']))
#print(canConstruct('skateboard', ['bo', 'rd', 'ate', 't', 'ska', 'sk', 'boar']))
#print(canConstruct('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef', ['e', 'ee', 'eee', 'eeee', 'eeeee', 'eeeeee']))


# A combinatorial problem
# Runtime: O(n^m * m) -> without DP, Space: O(m^2)
# Runtime: O(m^2 * n) -> With DP, Space O(m^2)
def countConstruct(target, array, memo={}):
	if target in memo:
		return memo[target]
	if target == '':
		return 1
	else:
		count = 0
		for word in array:
			if len(word) <= len(target) and target[:len(word)] == word:
				count += countConstruct(target[len(word):], array, memo)
	memo[target] = count
	return count

# print(countConstruct('purple', ['purp', 'p', 'ur', 'le', 'purpl'])) # 2
# print(countConstruct('skateboard', ['bo', 'rd', 'ate', 't', 'ska', 'sk', 'boar'])) # 0
# print(countConstruct('enterapotentpot', ['a', 'p', 'ent', 'enter', 'ot', 'o', 't'])) # 4
# print(countConstruct('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef', ['e', 'ee', 'eee', 'eeee', 'eeeee', 'eeeeee'])) # 0


# Runtime: O(n^m * m) -> Without DP, Space O(m^2)
# Runtime: O(n^m * m) -> With DP, Space(Recursive Callstack): O(m)
# This cannot do any better than EXPONENTIAL because the worse case
# produces a result of exponential size.
def allConstruct(target, array, memo={}):
	if target in memo:
		return memo[target]
	if (target == ''):
		return [[]]
	else:
		allWords = []
		for word in array:
			if (len(word) <= len(target) and target[:len(word)] == word):
				targetWays = allConstruct(target[len(word):], array, memo)
				if (targetWays != None and len(targetWays) > 0):
					targetWays[0] += [word]
					allWords += targetWays
		
		memo[target] = allWords
		return allWords

#print(len(allConstruct('purple', ['purp', 'p', 'ur', 'le', 'purpl']))) # 2
#print(len(allConstruct('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef', ['e', 'ee', 'eee', 'eeee', 'eeeee', 'eeeeee']))) # 0
#print(len(allConstruct('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', ['e', 'ee', 'eee', 'eeee', 'eeeee', 'eeeeee']))) # ???

# Tabulation
def canConstructTab(target, array):
	tabArr = [False] * (len(target) + 1)
	tabArr[0] = True

	for i in range(0, len(target)):
		if (tabArr[i]):
			for word in array:
				if (len(target) - i >= len(word) and target[i:i + len(word)] == word):
					tabArr[i + len(word)] = True

	return (tabArr, tabArr[len(target)])

#print(canConstructTab('skateboard', ['bo', 'rd', 'ate', 't', 'ska', 'sk', 'boar']))
#print(canConstructTab('abcdef', ['ab', 'abc', 'cd', 'def', 'abcd']))


# O(m^2 * n) -> compaing the string at every iteration, iterating through the numbers array, doing this m times
# O(m) the length of the tabulation table
def countConstructTab(target, array):
	tabArr = [0] * (len(target) + 1)
	tabArr[0] = 1
	for i in range(0, len(target)):
		if (tabArr[i]):
			for word in array:
				if (len(word) <= len(target) - i and word == target[i: i + len(word)]):
					tabArr[i + len(word)] = tabArr[i] + tabArr[i + len(word)]

	return (tabArr, tabArr[len(target)])

#print(countConstructTab('purple', ['purp', 'p', 'ur', 'le', 'purpl'])) # 2
#print(countConstructTab('abcdef', ['ab', 'abc', 'cd', 'def', 'abcd'])) # 1
#print(countConstructTab('eeeeeeeeeeeeeeeeeeeeeeeeeef', ['e', 'ee', 'eee', 'eeee', 'eeeee', 'eeeeee'])) # 1

