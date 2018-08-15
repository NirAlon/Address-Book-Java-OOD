
interface AddressBookEvent1
{ enum eventType
  { ADD(true), FIRST(true), LAST(true), NEXT(true),
	PREVIOUS(true), CLEAR(true), REVERSE(true),SWITCH(true); 
    private boolean doEvent;
    eventType(boolean doEvent)
    { this.doEvent = doEvent;
    }
    boolean getDoEvent()
    {  return doEvent;
    }
  }  
}