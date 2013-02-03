/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ProgrammaticUpdateLatch
{
	private State state;

	public ProgrammaticUpdateLatch()
	{
		state = State.NEUTRAL;
	}
	
	public void turnOn()
	{
		state = State.LOCKED;
	}
	
	public void turnOff()
	{
		state = State.NEUTRAL;
	}

	public boolean tryToPass()
	{
		boolean isPassible = state.isPassible();
		state = state.getNextState();
		return isPassible;
	}
	
	
	private static enum State
	{
		NEUTRAL{

			@Override
			public boolean isPassible()
			{
				return true;
			}

			@Override
			public State getNextState()
			{
				return NEUTRAL;
			}
			
		},
		
		LOCKED{

			@Override
			public boolean isPassible()
			{
				return true;
			}

			@Override
			public State getNextState()
			{
				return UNLOCKED;
			}
			
		},
		
		UNLOCKED{

			@Override
			public boolean isPassible()
			{
				return true;
			}

			@Override
			public State getNextState()
			{
				return UNLOCKED;
			}
			
		};

		public abstract boolean isPassible();

		public abstract State getNextState();
		
	}
}
